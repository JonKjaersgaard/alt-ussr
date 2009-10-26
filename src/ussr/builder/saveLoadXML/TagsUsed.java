package ussr.builder.saveLoadXML;

public enum TagsUsed {
   /*For description of modular robot morphology(shape)*/
   MODULES, /*First tag(by hierarchy), indicating that file keeps information about all modules in the morphology of modular robot*/
   MODULE, /*Second tag, separating each module description*/
   ID,     /*Third tag, global ID of module in simulation environment*/
   TYPE,
   NAME,
   ROTATION,
   ROTATION_QUATERNION,
   POSITION,
   POSITION_VECTOR,
   COMPONENTS,
   COLORS_COMPONENTS,
   CONNECTORS,
   COLORS_CONNECTORS,
   LABELS_MODULE,
   LABELS_CONNECTORS;
}
