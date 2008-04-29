package dcd.highlevel;

public class ByteCodeStreamBuilder implements OutputBuilder {

    public static class Packet {
        private byte[] content;
        public Packet(byte[] content) {
            this.content = content;
        }
    }

    public void addByteCode(ByteCode bc) {
        // TODO Auto-generated method stub
        // 
        throw new Error("Method not implemented");
    }

    public void addComment(String string) {
        // TODO Auto-generated method stub
        // 
        throw new Error("Method not implemented");
    }

    public void finish() {
        // TODO Auto-generated method stub
        // 
        throw new Error("Method not implemented");
    }

    public void finishFragment() {
        // TODO Auto-generated method stub
        // 
        throw new Error("Method not implemented");
    }

    public void scheduleFragmentSend(String fragment, boolean receiveLocally) {
        if(!receiveLocally) throw new Error("Non-local reception not supported");
        // TODO Auto-generated method stub
        // 
        throw new Error("Method not implemented");
    }

    public void startFragment(String programName, int size) {
        // TODO Auto-generated method stub
        // 
        throw new Error("Method not implemented");
    }

    public void startFragmentScheduling(String name) {
        // TODO Auto-generated method stub
        // 
        throw new Error("Method not implemented");
    }

}
