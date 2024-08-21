package com.yb.digilib.util;

import com.yb.digilib.model.Book;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class BookUtils {

    // Sérialiser les livres dans un fichier (fichier non classpath)
    public static void saveBooksToFile(List<Book> books, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Désérialiser les livres depuis un fichier (fichier non classpath)
    @SuppressWarnings("unchecked")
    public static List<Book> loadBooksFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(); // Retourner une liste vide plutôt que null
    }

    // Méthode pour lire des livres depuis un fichier dans le classpath
    @SuppressWarnings("unchecked")
    public static List<Book> loadBooksFromClasspath(String filePath) {
        List<Book> books = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(
                BookUtils.class.getResourceAsStream(filePath))) {

            if (ois != null) {
                // Lire tous les livres du fichier
                while (true) {
                    try {
                        Book book = (Book) ois.readObject();
                        books.add(book);
                    } catch (EOFException e) {
                        // Fin de fichier atteinte
                        break;
                    }
                }
            } else {
                System.err.println("Le fichier n'a pas été trouvé dans le classpath : " + filePath);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return books;
    }

}
