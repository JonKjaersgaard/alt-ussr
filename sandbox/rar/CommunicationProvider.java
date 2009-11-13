package rar;

public interface CommunicationProvider {
    public void broadcastMessage(byte[] message);
    public void delay(int time);
    public float getTime();
}
