package Hashing;

public class UniversalHash {

    // Size of the hash table (power of 2)
    public long tableSize;
    // Random matrix for hashing
    public  boolean[][] hashMatrix;
    
    public UniversalHash(long tableSize) {
        this.tableSize = tableSize;
        int matrixRows = (int) Math.ceil(Math.log(tableSize) / Math.log(2)); // Calculate rows based on table size (b)
        //System.out.println("Matrix rows are "+matrixRows);
        this.hashMatrix = generateRandomMatrix(matrixRows);
    }
    
    // Method to generate a random b-by-u matrix with 0s and 1s
    private boolean[][] generateRandomMatrix(int rows) {
        int u = 64; // Number of bits in a long integer
        boolean[][] matrix = new boolean[rows][u];
        //System.out.println("Matrix");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < u; j++) {
                matrix[i][j] = Math.random() < 0.5; // Random boolean value (0 or 1)
                // System.out.print(matrix[i][j]+" ");
            }
            //System.out.println();
        }
        return matrix;
    }
    // Method to generate a fingerprint for an object
    public long generateFingerprint(Object obj) {
        String word = obj.toString();
        //System.out.println("Word: " + word);
        long fingerprint = 0L;
        for (int i = 0; i < word.length(); i++) {
            char firstChar = word.charAt(i);
        //   char secondChar = word.charAt(i + 1);

            // Combine characters using bit shifting and OR
            fingerprint = fingerprint << 8; // Left shift by 8 bits
            fingerprint |= (long) firstChar << 8 ; // Left shift first char OR with second char
        }
        return fingerprint;
    }
    // Method to convert a long integer to a boolean array
    // Method to hash a key using matrix multiplication modulo 2
    public long hash(long key) {
        boolean[] keyBytes = longToBitArray(key);
        byte[] hashBytes = new byte[hashMatrix.length];
        long hashValue = 0;
        // //System.out.println("Key: "+key);
        for (int i = 0; i < hashMatrix.length; i++) {
            for (int j = 0; j < keyBytes.length; j++) {
                hashBytes[i] ^= (hashMatrix[i][j] && keyBytes[j])  ? 1 : 0; // Bitwise AND and XOR for modulo 2
            }
            // //System.out.println("HashBytes: "+hashBytes[i]);
        }
        // //System.out.println("HashBytes length: "+hashBytes.length);
        // Convert the byte array to a binary string
        String binaryString = byteArrayToBinaryString(hashBytes);
        //System.out.println("HashBytes String: "+binaryString);
        // Parse the binary string as a long value
        hashValue= Long.parseLong(binaryString, 2);
        //System.out.println("HashValue: "+hashValue);
        return hashValue % tableSize; // Reduce to valid hash table index
    }
    public static boolean[] longToBitArray(long number) {
        boolean[] bitArray = new boolean[64];

        // Extract each bit of the long number and store it in the bit array
        for (int i = 63; i >= 0; i--) {
            bitArray[i] = ((number >> i) & 1) == 1;
        }

        return bitArray;
    }
    // Method to convert a byte array to a binary string
    public String byteArrayToBinaryString(byte[] bytes) {
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            binary.append((val) == 0 ? 0 : 1);
            val <<= 1;
        }
        return binary.toString();
    }
    // Method to print the binary representation of a long integer
    public static void printBinary(long number) {
        String binary = Long.toBinaryString(number);
        // Ensure the binary string is 64 bits long
        binary = String.format("%64s", binary).replace(' ', '0');
        System.out.println("Binary representation of " + number + ": " + binary);
    }
 }
 