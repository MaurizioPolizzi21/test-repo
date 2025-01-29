// Ejemplo 1: Consulta de saldo básica
public class BasicBalanceQuery implements ServiceGrid {
    @ServiceMethod(name = "getAccountBalance")
    public BalanceResponse getBalance(AccountRequest request) {
        try {
            Connection conn = DatabaseFactory.getConnection();
            String query = "SELECT current_balance FROM accounts WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, request.getAccountNumber());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new BalanceResponse(
                    rs.getBigDecimal("current_balance"),
                    ResponseStatus.SUCCESS
                );
            }
            return new BalanceResponse(null, ResponseStatus.ACCOUNT_NOT_FOUND);
        } catch (Exception e) {
            throw new ServiceGridException("ERROR_GETTING_BALANCE", e);
        }
    }
}

