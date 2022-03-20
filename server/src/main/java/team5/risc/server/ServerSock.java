package team5.risc.server;

import java.util.ArrayList;
import team5.risc.common.*;
import java.net.*;
import java.io.*;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ServerSock {
    //List to keep track of I/O client streams list
    private ArrayList<ObjectOutputStream> objectOutputStreamList;
    private ArrayList<ObjectInputStream> objectInputStreamList;
    private ArrayList<DataOutputStream> dataOutputStreamList;
    private ArrayList<DataInputStream> dataInputStreamList;

    //Constructor to init list
    public ServerSock(){
      objectOutputStreamList = new ArrayList<>();
      objectInputStreamList = new ArrayList<>();
      dataOutputStreamList = new ArrayList<>();
      dataInputStreamList = new ArrayList<>();  
    }

    //Add clients stream to list
    public void init(ArrayList<Socket> clientSocketSet) throws IOException{
      for (Socket client : clientSocketSet) {
        objectOutputStreamList.add(new ObjectOutputStream(client.getOutputStream()));
        objectInputStreamList.add(new ObjectInputStream(client.getInputStream()));
        dataOutputStreamList.add(new DataOutputStream(client.getOutputStream()));
        dataInputStreamList.add(new DataInputStream(client.getInputStream()));
      }
    }

    //get objectInputStream for client
    public ObjectInputStream getObjIn(int i){
      return objectInputStreamList.get(i);
    }

    //get objectOutputStream for client
    public ObjectOutputStream getObjOut(int i){
      return objectOutputStreamList.get(i);
    }

    //get dataInputStream for client
    public DataInputStream  getDataIn(int i){
      return dataInputStreamList.get(i);
    }

    //get dataOutputStream for client
    public DataOutputStream getDataOut(int i){
      return dataOutputStreamList.get(i);
    }


    //Accept client connections
    public void AcceptConnection(int num_player, ServerSocket serverSocket, ArrayList<Socket> clientSocketSet) throws IOException{
      for (int i = 0; i < num_player; ++i) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Player " + i + " connected!");
        //System.out.println(clientSocket);
        clientSocketSet.add(clientSocket);
      }
    }

    public void close() throws IOException{
      for (DataInputStream stream : dataInputStreamList) {
        stream.close();
      }
      for (DataOutputStream stream : dataOutputStreamList) {
        stream.close();
      }
      for (ObjectInputStream stream : objectInputStreamList) {
        stream.close();
      }
      for (ObjectOutputStream stream : objectOutputStreamList) {
        stream.close();
      }
    }
}
