package Biblioteca;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArchivioBiblioteca {
    private List<ElementoCatalogo> archivio = new ArrayList<>();

    public void aggiungiElemento(ElementoCatalogo elemento) {
        archivio.add(elemento);
    }

    public void rimuoviElemento(String codiceISBN) {
        archivio.removeIf(elemento -> elemento.getCodiceISBN().equals(codiceISBN));
    }

    public ElementoCatalogo ricercaPerISBN(String codiceISBN) {
        return archivio.stream()
                .filter(elemento -> elemento.getCodiceISBN().equals(codiceISBN))
                .findFirst()
                .orElse(null);
    }

    public List<ElementoCatalogo> ricercaPerAnnoPubblicazione(int anno) {
        return archivio.stream()
                .filter(elemento -> elemento.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());
    }

    public List<ElementoCatalogo> ricercaPerAutore(String autore) {
        return archivio.stream()
                .filter(elemento -> elemento instanceof Libro && ((Libro) elemento).getAutore().equals(autore))
                .collect(Collectors.toList());
    }

    public void salvaSuDisco(String nomeFile) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFile))) {
            oos.writeObject(archivio);
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio su disco: " + e.getMessage());
        }
    }

    public void caricaDaDisco(String nomeFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFile))) {
            archivio = (List<ElementoCatalogo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante il caricamento dal disco: " + e.getMessage());
        }
    }
}
