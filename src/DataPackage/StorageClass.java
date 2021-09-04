package DataPackage;

public class StorageClass {
    public static int keyPosition = 0;

    public static String errorEmptyField = "Field can`t be empty! Try again.";
    public static String warningKeyExist = "This Key already exist, Do you want to change Value? (y/n)";
    public static String yesOrNo = "Y/N?";
    public static String keyIs = "Key is: ";
    public static String skippedText = "Skipped.";
    public static String arrayEmptyText = "Skipped.";
    public static String indexForDeleteText = "Input index for delete: ";
    public static String valueInputText = "Input array of values, separated by ',': ";
    public static String helpText = """
                        /new - type new key.
                        /delete_index - type index for delete.
                        /delete_item - type name for delete.
                        /end - program exit.""";
    public static String indexOutOfRangeText = "Index out of range!";
    public static String keyDoesNotExist = "Key does not exist.";

}
