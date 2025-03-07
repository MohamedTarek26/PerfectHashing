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
    // // Method to generate a key for an object
    // public long generateKey(Object obj) {
    //     String word = obj.toString();
    //     //System.out.println("Word: " + word);
    //     long key = 0L;
    //     for (int i = 0; i < word.length(); i++) {
    //         char firstChar = word.charAt(i);
    //     //   char secondChar = word.charAt(i + 1);

    //         // Combine characters using bit shifting and OR
    //         key = key << 8; // Left shift by 8 bits
    //         key |= (long) firstChar << 8 ; // Left shift first char OR with second char
    //     }
    //     return key;
    // }
    // Method to generate a key for an object
    public long generateKey(Object obj) {
        String word = obj.toString();
        //System.out.println("Word: " + word);
        int offset = 0;
        int indx = (int) Math.ceil(word.length() / 8.0);
        long[] keys = new long[indx];
        long key = 0L;
        for (int j = 0; j < indx; j++) {
            // keys[i] = generateKey(word.substring(offset, offset+8));
            for (int i = 0; i < 8 && i + offset < word.length(); i++) {
                if(i+offset>=word.length()){
                    break;
                }
                char ch = word.charAt(i + offset);
                // Combine characters using bit shifting and OR
                keys[j] = keys[j] << 8; // Left shift by 8 bits
                keys[j] |= (long) ch;   // OR with the current character
            }
            offset += 8;
        }
        for (int i = 0; i < keys.length; i++) {
            key = key ^ keys[i];
        }
        return key;
    }
    // Method to convert a long integer to a boolean array
    // Method to hash a key using matrix multiplication modulo 2
    public long hash(long key) {
        boolean[] keyBits = longToBitArray(key);
        boolean[] hashBits = new boolean[hashMatrix.length];
        long hashValue = 0;
        // //System.out.println("Key: "+key);
        for (int i = 0; i < hashMatrix.length; i++) {
            for (int j = 0; j < keyBits.length; j++) {
                hashBits[i] ^= (hashMatrix[i][j] && keyBits[j])  ? true : false; // Bitwise AND and XOR for modulo 2
            }
            // //System.out.println("hashBits: "+hashBits[i]);
        }
        // //System.out.println("hashBits length: "+hashBits.length);
        // Convert the byte array to a binary string
        // String binaryString = byteArrayToBinaryString(hashBits);
        //System.out.println("hashBits String: "+binaryString);
        // Parse the binary string as a long value
        // hashValue= Long.parseLong(binaryString, 2);
        hashValue = bitArrayToLong(hashBits);
        //System.out.println("HashValue: "+hashValue);
        return hashValue % tableSize; // Reduce to valid hash table index
    }
    public boolean[] longToBitArray(long number) {
        boolean[] bitArray = new boolean[64];

        // Extract each bit of the long number and store it in the bit array
        for (int i = 63; i >= 0; i--) {
            bitArray[i] = ((number >> i) & 1) == 1;
        }

        return bitArray;
    }
    // Method to convert a bit array to a long integer
    public long bitArrayToLong(boolean[] bitArray) {
        long number = 0;

        // Convert the bit array to a long number
        for (int i = 0; i < bitArray.length; i++) {
            number = (number << 1) | (bitArray[i] ? 1 : 0);
        }

        return number;
    }
    // // Method to convert a byte array to a binary string
    // public String byteArrayToBinaryString(byte[] bytes) {
    //     StringBuilder binary = new StringBuilder();
    //     for (byte b : bytes) {
    //         int val = b;
    //         binary.append((val) == 0 ? 0 : 1);
    //         val <<= 1;
    //     }
    //     return binary.toString();
    // }
    // Method to print the binary representation of a long integer
    public static void printBinary(long number) {
        String binary = Long.toBinaryString(number);
        // Ensure the binary string is 64 bits long
        binary = String.format("%64s", binary).replace(' ', '0');
        System.out.println("Binary representation of " + number + ": " + binary);
    }
 }
 
