const jwt = require("jsonwebtoken");

module.exports = (req, res, next) => {
  // Check if token is valid
  const token = req.headers.authorization;
  if (!token) {
    return res.status(401).json({
      error: "Você precisa estar autenticado para acessar esta rota!",
    });
  }

  const tokenValue = token.split(" ")[1];
  jwt.verify(tokenValue, process.env.SECRET, (err, decoded) => {
    if (err) {
      return res.status(401).json({
        error: "Token inválido!",
      });
    }
    req.user = decoded;
    next();
  });
};
