from ..models.times_model import times_bd, times_get_response, times_model, user_team_model
from ..repository.times_repository import times_repository
from ..repository.userteam_repository import userteam_repository
from ..repository.usuario_repository import user_repository
from ..repository.profile_repository import profille_repository


class times_services(object):
    _times_repository: times_repository = times_repository()
    _usuario_repository: user_repository = user_repository()
    _userteam_repository: userteam_repository = userteam_repository()
    _profiles_repository: profille_repository = profille_repository()

    def __init__(self):
        pass

    def buscar_times(self):
        times = self._times_repository.get()
        profiles = self._profiles_repository.get();
        
        timeResponseList = []

        for time in times:
            timeResponse = self.mapToTimeResponse(time)
            userTeams = self._userteam_repository.get_user_teams_by_team_id(
                time.id)
            for userTeam in userTeams:
                user = self._usuario_repository.get_by_id(userTeam.id_user);
                timeResponse.userName.append(user.usuario+' ['+profiles[userTeam.id_profile-1].perfil+']')
                timeResponse.id_users.append(user.id)

            timeResponseList.append(timeResponse)

        return timeResponseList

    def mapToTimeResponse(self, time):
        timeResponse = times_get_response()
        timeResponse.id = time.id
        timeResponse.id_group = time.id_group
        timeResponse.times = time.times
        timeResponse.userName = []
        timeResponse.id_users = []
        return timeResponse

    def buscar_id_times(self, id):
        time = self._times_repository.busca_id_times(id)
        if (not time):
            return None
        else:
            user_teams = self._userteam_repository.get_user_teams_by_team_id(
                time.id)

        model = times_model()
        model.id = time.id
        model.times = time.times
        model.times_model = []

        for u_t in user_teams:
            u_t.user = self._usuario_repository.get_by_id(u_t.id_user).usuario
            u_t.profile = self._profiles_repository.find(u_t.id_profile).perfil

            model.times_model.append(u_t)

        return model

    def post_times(self, model: times_model):
        modelToInsert = times_bd()
        modelToInsert.id = model.id
        modelToInsert.times = model.times
        self._times_repository.post_times(modelToInsert)

        self.create_user_team(modelToInsert.id, model.times_model)

    def put_times(self, model: times_model):
        modelToInsert = times_bd()
        modelToInsert.id = model.id
        modelToInsert.times = model.times

        self._times_repository.update(modelToInsert)
        self.update_user_team(modelToInsert.id, model.times_model)

    def delete(self, id: int):
        self.delete_user_team(id)
        return self ._times_repository.delete_id_times(id)

    def create_user_team(self, idGroup: int, userTeams: list[user_team_model]):
        for userTeam in userTeams:
            userTeam.id_team = idGroup
            self._userteam_repository.create(userTeam)

    def update_user_team(self, idTeam: int, userTeams: list[user_team_model]):
        self.delete_user_team(idTeam)
        for userTeam in userTeams:
            self._userteam_repository.create(userTeam)

    def delete_user_team(self, idTeam: int):
        allUserProfiles = self._userteam_repository.get_user_teams_by_team_id(
            idTeam)

        for userProfile in allUserProfiles:
            self._userteam_repository.delete(userProfile.id)
