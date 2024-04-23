package Hashing;

public class BatchSuceessFailure {
    private int success;
    private int failure;

    public BatchSuceessFailure(int success, int failure) {
        this.success = success;
        this.failure = failure;
    }

    public int getSuccess() {
        return success;
    }

    public int getFailure() {
        return failure;
    }
}