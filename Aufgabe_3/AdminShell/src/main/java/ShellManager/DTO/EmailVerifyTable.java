package ShellManager.DTO;

import java.util.List;

/**
 * Created by dima on 23.05.16.
 */
public class EmailVerifyTable {

    private final String tableHeaderLine = "==============================================\n";
    private List<TokenObjectDTO> userList;


    public EmailVerifyTable(List<TokenObjectDTO> userList) {
        this.userList = userList;
    }

    public List<TokenObjectDTO> getUserList() {
        return userList;
    }

    public String printTable(){
        String out = tableHeaderLine;

        // build header
        out += "|token\t\t|userID\t\t|isValid\t\t|isUsed\n";
        out += tableHeaderLine;

        for (TokenObjectDTO dto : userList){
            String column = "|"+dto.getToken()+"\t|"+dto.getUserID()+"\t|"+dto.isValid()+"\t|"+dto.isUsed()+"\n";
            out +=column;
        }

        return out;
    }
}
