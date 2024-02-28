let getAvaliacao = async () => {
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    var avaliacao;

    await fetch(localURL + '/api/v1/avaliacao/', requestOptions)
        .then(response => response.text())
        .then(result => avaliacao = result)
        .catch(error => console.log('error', error));

    return JSON.parse(avaliacao);
}

let getFinishedSprints = async (user_id) => {
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    var sprints;

    await fetch(localURL + `/api/v1/sprint/finished/${user_id}`, requestOptions)
        .then(response => response.text())
        .then(result => sprints = result)
        .catch(error => console.log('error', error));

    return JSON.parse(sprints);
}

let getAlreadyRatedSprints = async (user_id) => {
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    var sprints;

    await fetch(localURL + `/api/v1/avaliacaoUsuario/already-rated/${user_id}`, requestOptions)
    .then(response => response.text())
    .then(result => sprints = result)
    .catch(error => console.log('error', error));

    return JSON.parse(sprints);
}


let getAvaliacaoById = async (id) => {
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    var avaliacao;

    await fetch(localURL + '/api/v1/avaliacao/' + id, requestOptions)
        .then(response => response.text())
        .then(result => avaliacao = result)
        .catch(error => console.log('error', error));

    return JSON.parse(avaliacao);
}

let deleteAvaliacao = async (id) => {
    var requestOptions = {
        method: 'DELETE',
        redirect: 'follow'
    };

    await fetch(localURL + '/api/v1/avaliacao/' + id, requestOptions)
        .then(response => response.text())
        .then(result => callbackHandler(result, 'Avaliação deletada com sucesso'))
        .catch(error => console.log('error', error));

    window.location.reload();
}

let createAvaliacao = async (avaliacoes) => {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: JSON.stringify(avaliacoes),
        redirect: 'follow'
    };

    
    await fetch(localURL + "/api/v1/avaliacaoUsuario/", requestOptions)
        .then(response => response.text())
        .then(result => callbackHandler(result, 'Avaliacao efetuada com sucesso'))
        .catch(error => console.log('error', error))
}

let updateAvaliacao = async (avaliacoes) => {
    var myHeaders = new Headers();
    myHeaders.append("Content-type", "application/json");

    var raw = JSON.stringify(avaliacoes);

    var requestOptions = {
        method: 'PUT',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    await fetch(localURL + "/api/v1/avaliacao/", requestOptions)
        .then(response => response.text())
        .then(result => callbackHandler(result, 'Atualização realizada com sucesso'))
        .catch(error => console.log('error', error));
}


function createBody(id, rated_user, sprint_id, rated_by, grade1, grade2, grade3, grade4, grade5, comment) {
    return JSON.stringify({
        "id": id,
        "rated_user": rated_user.toString(),
        "sprint_id": sprint_id.toString(),
        "rated_by": rated_by.toString(),
        "grade01": grade1,
        "grade02": grade2,
        "grade03": grade3,
        "grade04": grade4,
        "grade05": grade5,
        "comment": comment.toString(),
    });
}

function callbackHandler(response, successMessage) {
    if (response == 200) {
        alert(successMessage)
        window.location.reload()
        return;
    }

    alert(response);
}