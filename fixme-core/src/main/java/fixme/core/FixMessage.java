package fixme.core;

public record FixMessage(
    int senderId,
    int receiverId,
    String content,
    int checkSum
){
}
