package team5.risc.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class RISCServer {
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Socket client;

    public RISCServer(String ip, int port) throws UnknownHostException, IOException {
        client = new Socket(ip, port);
        System.out.println(client.getRemoteSocketAddress());
        objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        objectInputStream = new ObjectInputStream(client.getInputStream());
        dataOutputStream = new DataOutputStream(client.getOutputStream());
        dataInputStream = new DataInputStream(client.getInputStream());
    }

    public int readInt() throws IOException {
        return (int) dataInputStream.readInt();
    }

    public void writeInt(int i) throws IOException {
        dataOutputStream.writeInt(i);
    }

    public String readUTF() throws IOException {
        return dataInputStream.readUTF();
    }

    public void writeUTF(String str) throws IOException {
        dataOutputStream.writeUTF(str);
        dataOutputStream.flush();
    }

    public Object readObject() throws ClassNotFoundException, IOException {
        return objectInputStream.readObject();
    }

    public void writeObject(Object obj) throws IOException {
        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
    }

    public boolean isAvailable() {
        // currently not implemented, as this is just demo used in a software test
        return false;
    }
    public int getUniqueId() {
        return 42;
    }

    public void close() throws IOException {
        objectInputStream.close();
        objectOutputStream.close();
        dataInputStream.close();
        dataOutputStream.close();
        client.close();
    }
  }