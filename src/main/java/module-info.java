module edu.ufp.inf {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires edu.princeton.cs.algs4;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires spring.security.core;
    opens edu.ufp.inf.person_user to javafx.base;
    opens edu.ufp.inf.paper_author to javafx.base;
    opens edu.ufp.inf.javafx to javafx.graphics;
    exports edu.ufp.inf.javafx.login to javafx.graphics;

    opens edu.ufp.inf.javafx.loginFxml to javafx.graphics, javafx.fxml;
    exports edu.ufp.inf.javafx.loginFxml to javafx.graphics, javafx.fxml;

    opens edu.ufp.inf.javafx.home to javafx.graphics, javafx.fxml;
    exports edu.ufp.inf.javafx.home to javafx.graphics, javafx.fxml;
}