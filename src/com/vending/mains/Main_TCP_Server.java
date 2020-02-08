package com.vending.mains;

import com.vending.helper.RemoteStartCLI;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main_TCP_Server {
    public static void main(String[] args) throws IOException {

        int port0 = 13370;
        ServerSocket serverSocket0 = new ServerSocket(port0);
        Socket client0 = serverSocket0.accept();

        int port1 = 13371;
        ServerSocket serverSocket1 = new ServerSocket(port1);
        Socket client1 = serverSocket1.accept();

        int port2 = 13372;
        ServerSocket serverSocket2 = new ServerSocket(port2);
        Socket client2 = serverSocket2.accept();

        InputStream pipedInputStream = client0.getInputStream();
        ObjectOutputStream objectOutputStreamCakes = new ObjectOutputStream(client1.getOutputStream());
        ObjectOutputStream objectOutputStreamManufacturers = new ObjectOutputStream(client2.getOutputStream());

        RemoteStartCLI remoteStartCLI = new RemoteStartCLI(pipedInputStream, System.out, objectOutputStreamCakes, objectOutputStreamManufacturers);
        remoteStartCLI.startCli();
    }
}
