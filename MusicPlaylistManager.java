
import java.util.Scanner;
class Song {
    String title;
    String artist;
    Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }
}
class SongNode {
    Song song;
    SongNode next;
    SongNode(Song song) {
        this.song = song;
        this.next = null;
    }
}
class Playlist {
    SongNode head;
    void addSong(String title, String artist) {
        Song newSong = new Song(title, artist);
        SongNode newNode = new SongNode(newSong);
        if (head == null) {
            head = newNode;
        } else {
            SongNode temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        System.out.println("Song added successfully!");
    }
    void displaySongs() {
        if (head == null) {
            System.out.println("Playlist is empty.");
            return;
        }
        SongNode temp = head;
        int number = 1;
        while (temp != null) {
            System.out.println(number + ". " + temp.song.title
                    + " - " + temp.song.artist);
            temp = temp.next;
            number++;
        }
    }
    Song searchSong(String title) {
        SongNode temp = head;
        while (temp != null) {
            if (temp.song.title.equalsIgnoreCase(title)) {
                return temp.song;
            }
            temp = temp.next;
        }
        return null;
    }
    void removeSong(String title) {
        if (head == null) {
            System.out.println("Playlist is empty.");
            return;
        }
        if (head.song.title.equalsIgnoreCase(title)) {
            head = head.next;
            System.out.println("Song removed successfully!");
            return;
        }
        SongNode temp = head;
        while (temp.next != null) {
            if (temp.next.song.title.equalsIgnoreCase(title)) {
                temp.next = temp.next.next;
                System.out.println("Song removed successfully!");
                return;
            }
            temp = temp.next;
        }
        System.out.println("Song not found.");
    }
    void sortSongs() {
        if (head == null || head.next == null) {
            System.out.println("Not enough songs to sort.");
            return;
        }
        boolean swapped;
        do {
            swapped = false;
            SongNode temp = head;
            while (temp.next != null) {
                if (temp.song.title.compareToIgnoreCase(
                        temp.next.song.title) > 0) {
                    Song temporarySong = temp.song;
                    temp.song = temp.next.song;
                    temp.next.song = temporarySong;
                    swapped = true;
                }
                temp = temp.next;
            }
        } while (swapped);
        System.out.println("Playlist sorted alphabetically!");
    }
}
class StackNode {
    Song song;
    StackNode next;
    StackNode(Song song) {
        this.song = song;
        this.next = null;
    }
}
class SongStack {
    StackNode top;
    void push(Song song) {
        StackNode newNode = new StackNode(song);
        newNode.next = top;
        top = newNode;
    }
    void pop() {
        if (isEmpty()) {
            System.out.println("Recently played stack is empty.");
            return;
        }
        System.out.println("Removed from history: "
                + top.song.title);

        top = top.next;
    }
    void peek() {
        if (isEmpty()) {
            System.out.println("Recently played stack is empty.");
        } else {
            System.out.println("Last played: "
                    + top.song.title + " - " + top.song.artist);
        }
    }
    boolean isEmpty() {
        return top == null;
    }
    void displayHistory() {
        if (isEmpty()) {
            System.out.println("No recently played songs.");
            return;
        }
        StackNode temp = top;
        int number = 1;
        System.out.println("Recently Played (newest first):");
        while (temp != null) {
            System.out.println(number + ". "
                    + temp.song.title + " - " + temp.song.artist);
            temp = temp.next;
            number++;
        }
    }
}
public class MusicPlaylistManager {
    public static void main(String[] args) {
        Playlist playlist = new Playlist();
        SongStack history = new SongStack();

        try (Scanner input = new Scanner(System.in)) {
            int choice;
            do {
                System.out.println("\n===== MUSIC PLAYLIST MANAGER =====");
                System.out.println("1. Add Song");
                System.out.println("2. Remove Song");
                System.out.println("3. Display Playlist");
                System.out.println("4. Search Song");
                System.out.println("5. Sort Playlist");
                System.out.println("6. Play Song");
                System.out.println("7. View Recently Played");
                System.out.println("8. Undo Last Play (Pop)");
                System.out.println("9. View Last Played Song (Peek)");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                while (!input.hasNextInt()) {
                    System.out.println("Please enter a number.");
                    input.next();
                    System.out.print("Enter your choice: ");
                }
                choice = input.nextInt();
                input.nextLine();
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter song title: ");
                        String title = input.nextLine().trim();
                        System.out.print("Enter artist name: ");
                        String artist = input.nextLine().trim();
                        if (title.isEmpty() || artist.isEmpty()) {
                            System.out.println(
                                    "Title and artist cannot be empty.");
                        } else {
                            playlist.addSong(title, artist);
                        }
                    }
                    case 2 -> {
                        System.out.print("Enter title to remove: ");
                        String title = input.nextLine().trim();
                        if (title.isEmpty()) {
                            System.out.println("Title cannot be empty.");
                        } else {
                            playlist.removeSong(title);
                        }
                    }
                    case 3 -> playlist.displaySongs();
                    case 4 -> {
                        System.out.print("Enter title to search: ");
                        String title = input.nextLine().trim();
                        if (title.isEmpty()) {
                            System.out.println("Title cannot be empty.");
                        } else {
                            Song found = playlist.searchSong(title);
                            if (found != null) {
                                System.out.println("Song found: "
                                        + found.title + " - " + found.artist);
                            } else {
                                System.out.println("Song not found.");
                            }
                        }
                    }
                    case 5 -> playlist.sortSongs();
                    case 6 -> {
                        System.out.print("Enter song title to play: ");
                        String title = input.nextLine().trim();
                        if (title.isEmpty()) {
                            System.out.println("Title cannot be empty.");
                        } else {
                            Song found = playlist.searchSong(title);
                            if (found != null) {
                                history.push(found);
                                System.out.println("Now playing: "
                                        + found.title + " - " + found.artist);
                            } else {
                                System.out.println("Song not found.");
                            }
                        }
                    }
                    case 7 -> history.displayHistory();
                    case 8 -> history.pop();
                    case 9 -> history.peek();
                    case 0 -> System.out.println("Goodbye!");
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 0);
        }
    }
}