package ShellManager.DTO;


import java.util.List;

public class UserPassResetTable{

    private final String tableHeaderLine = "==============================================\n";
    private List<TokenObjectDTO> userList;

    public UserPassResetTable(List<TokenObjectDTO> userList) {
        this.userList = userList;
    }

    public List<TokenObjectDTO> getUsers(){
        return userList;
    }

    public String printTable(){

        String out = tableHeaderLine;
        out += "|token\t\t|userID\t\t|isValid\t\t|isUsed\n";
        out += tableHeaderLine;

        for (TokenObjectDTO dto : userList){
            String column = "|"+dto.getToken()+"\t|"+dto.getUserID()+"\t|"+dto.isValid()+"\t|"+dto.isUsed()+"\n";
            out +=column;
        }
        return out;
    }
}