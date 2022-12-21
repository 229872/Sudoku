open module ModelProject {
    requires java.desktop;
    requires org.apache.commons.lang3;
    requires org.slf4j;

    exports pl.component.model.main;
    exports pl.component.model.algorithm;
    exports pl.component.exceptions;
    exports pl.component.model.elements;
    exports pl.component.dao;
}