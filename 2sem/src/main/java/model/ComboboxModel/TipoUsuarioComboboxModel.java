package model.ComboboxModel;

public class TipoUsuarioComboboxModel {
    private int id;
    private String descricao;

    public TipoUsuarioComboboxModel(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public TipoUsuarioComboboxModel() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
