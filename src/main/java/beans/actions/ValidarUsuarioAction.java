package beans.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;

@Results({
    @Result(name = "success", location = "/WEB-INF/content/bienvenido.jsp"),
    @Result(name = "input", location = "/WEB-INF/content/login.jsp")//******
})

public class ValidarUsuarioAction extends ActionSupport {

    private String usuario;
    private String password;

    @Action("validarUsuario")
    public String execute() {

        if ("admin".equals(this.usuario)) {
            addActionMessage(getText("usuario.valido"));
            return SUCCESS;
        } else {
            return INPUT;
        }
    }

    @Override
    public void validate() {
        if (this.usuario == null || "".equals(this.usuario.trim())) {
            addFieldError("usuario", getText("val.usuario"));
        } else if (!usuarioValido()) {
            addActionError(getText("usuario.invalido"));
        }

        //Lógica para el manejo de la validación del password dependiendo de cual sea el error.
        switch (passwordValido(this.password)) {
            case 1:
                addFieldError("password", getText("val.passwd"));
                break;
            case 2:
                addFieldError("password", getText("val.passwd.min.lenght"));
            default:
                break;
        }

    }

    public boolean usuarioValido() {
        return "admin".equals(this.usuario);
    }

    //"12345".equals(this.password) (mostrar mensaje de error dependiendo de cual sea el error)
    public int passwordValido(String password) {
        int x = 0;

        if (password == null || "".equals(password.trim())) {
            x = 1;
            return x;
        } else if (password.length() < 6) {
            x = 2;
            return x;
        }

        return x;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
