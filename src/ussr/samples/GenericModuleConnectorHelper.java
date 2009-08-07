package ussr.samples;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;

public class GenericModuleConnectorHelper {

    public static abstract class ModuleConnectorMapper {
        private String prefix;
        public abstract List<ModuleConnection> getConnections(List<ModulePosition> positions);
        public ModuleConnectorMapper(String prefix) { this.prefix = prefix; }
        public String getPrefix() { return prefix; }
    }
    
    private static List<ModuleConnectorMapper> connectorMappers;
    
    public static synchronized List<ModuleConnectorMapper> getConnectorMappers() {
        if(connectorMappers==null) {
            connectorMappers = new ArrayList<ModuleConnectorMapper>();
            connectorMappers.add(new ATRONConnectorMapper());
        }
        return connectorMappers;
    }
    
    public List<ModuleConnection> computeAllConnections(List<ModulePosition> positions) {
        List<ModulePosition> positionsCopy = new ArrayList<ModulePosition>(positions);
        List<ModuleConnection> result = new ArrayList<ModuleConnection>();
        synchronized(getConnectorMappers()) {
            for(ModuleConnectorMapper mapper: getConnectorMappers()) {
                String prefix = mapper.getPrefix();
                List<ModulePosition> filtered = findAndRemoveMatches(prefix,positionsCopy);
                result.addAll(mapper.getConnections(filtered));
            }
        }
        if(positionsCopy.size()>0)
            throw new Error("No handler for remaining connections: "+positionsCopy);
        System.out.println("Result: "+result);
        return result;
    }
    
    private List<ModulePosition> findAndRemoveMatches(String prefix, List<ModulePosition> positions) {
        List<ModulePosition> filtered = new ArrayList<ModulePosition>();
        ModulePosition pos;
        Iterator<ModulePosition> imp = positions.iterator();
        while(imp.hasNext()) {
            pos = imp.next();
            if(pos.getType().startsWith(prefix)) {
                filtered.add(pos);
                imp.remove();
            }
        }
        return filtered;
    }
    
    private static class ATRONConnectorMapper extends ModuleConnectorMapper {
        public ATRONConnectorMapper() { super("ATRON"); }
        @Override
        public List<ModuleConnection> getConnections(List<ModulePosition> positions) {
            ATRONBuilder builder = new ATRONBuilder();
            return builder.allConnections(positions);
        }

    }

}
