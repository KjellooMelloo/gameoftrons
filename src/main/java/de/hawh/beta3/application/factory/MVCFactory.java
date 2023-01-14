package de.hawh.beta3.application.factory;

public class MVCFactory {

    public static Object getInterface(String interf, boolean remote) {
        switch (interf.toLowerCase()) {
            case "imodel": return IModelFactory.getModel(remote);
            case "imodelview": return IModelViewFactory.getModelView(remote);
            case "imodelcontroller": return IModelControllerFactory.getModelController(remote);
            default: return null;
        }
    }
}
