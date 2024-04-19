package edu.jsu.mcis.cs310.tas_sp24;
import java.util.zip.CRC32;

/**
 * <p> String id and String description </p>
 **/
public class Badge {

    public final String id;
    private final String description;

    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }
    
/**
 * <p> Constructor for badge to generate and the description </p>
 * @param description 
 **/
    // New constructor for creating badge with generated ID
    public Badge(String description) {
        this.id = generateBadgeId(description);
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
/**
 * <p> String builder to append id and description </p>
 * @return 
 **/
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');

        return s.toString();

    }
    
    
    // Method to generate badge ID using CRC-32 checksum
    private String generateBadgeId(String description) {
        CRC32 crc32 = new CRC32();
        crc32.update(description.getBytes());
        long checksum = crc32.getValue();
        // Ensure the ID is exactly eight hexadecimal digits in length
        return String.format("%08X", checksum);
    }
}
