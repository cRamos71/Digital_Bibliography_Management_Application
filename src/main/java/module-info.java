module edu.ufp {
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

    opens edu.ufp.inf.lp2.p06_gui_javafx.helloword to javafx.fxml;
    exports edu.ufp.inf.p06_gui_javafx.helloword;
}