# Simple Java Server (Java 8)

Ini adalah proyek yang sama seperti [Simple Java Server](https://github.com/arifrahman42/SimpleJavaServer) namun dikembangkan dan disesuaikan untuk mendukung [Java JDK 8](https://www.oracle.com/java/technologies/javase/8-relnotes.html).

Beberapa perbedaan yang mencolok adalah proyek ini dikembangkan menggunakan dependensi library [Jetty](https://jetty.org/) untuk mengaktifkan servlet karena akses pada HttpServer (*com.sun.net.httpserver.HttpServer*) masih terbatas pada Java 8 dan memerlukan ekstra konfigurasi agar dapat mengaktifkannya. [↗](https://stackoverflow.com/questions/41099332/java-httpserver-error-access-restriction-the-type-httpserver-is-not-api/41099745#41099745)

Pada proyek ini, saya menggunakan [Jetty v.9.4.0](https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-distribution/9.4.0.v20161208/jetty-distribution-9.4.0.v20161208.zip) (8 Agustus 2016). Lihat pada folder [lib](https://github.com/arifrahman42/SimpleJavaServer-Java8/tree/main/lib), apa saja dependensi library Jetty yang saya gunakan pada proyek ini.

Program ini dikembangkan menggunakan [IDE Visual Studio Code](https://code.visualstudio.com/). Sebelum meng-compile dan menjalankan program ini:
1. Tekan tombol **Ctrl + Shift + P** lalu ketik "***Java: Open Project Settings***" (pastikan [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) terinstal);
2. Setel JDK Runtime ke **JavaSE-1.8** (pastikan [JDK Java 8](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html) terinstal);
3. Pada tab Compiler, **hapus centang** pada "*Use --release option for cross-compilation*" & "*Enable preview features*", dan setel ***source*** serta ***target compatibility*** menjadi **1.8**.

Setelah itu untuk meng-compile project, tekan tombol **Ctrl + Shift + B** karena saya telah melakukan penyesuaian compile project pada file [tasks.json](https://github.com/arifrahman42/SimpleJavaServer-Java8/blob/main/.vscode/tasks.json).

#