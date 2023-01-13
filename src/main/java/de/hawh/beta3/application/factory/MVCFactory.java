package de.hawh.beta3.application.factory;

public class MVCFactory {

    public static Object getInterface(String interf, String type) {
        switch (interf.toLowerCase()) {
            case "imodel": return IModelFactory.getModel(type);
            case "imodelview": return IModelViewFactory.getModelView(type);
            case "imodelcontroller": return IModelControllerFactory.getModelController(type);
            default: return null;
        }
    }
}
