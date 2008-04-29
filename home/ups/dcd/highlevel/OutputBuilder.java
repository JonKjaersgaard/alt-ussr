package dcd.highlevel;

public interface OutputBuilder {

    void finish();

    void addComment(String string);

    void startFragment(String programName, int size);

    void finishFragment();

    void scheduleFragmentSend(String fragment, boolean receiveLocally);

    void startFragmentScheduling(String name);

    void addByteCode(ByteCode bc);

}
