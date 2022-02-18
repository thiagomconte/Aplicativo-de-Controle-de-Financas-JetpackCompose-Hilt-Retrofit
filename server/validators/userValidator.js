const User = require("../models/User");

exports.validateRegister = async (req, res, next) => {
  const { name, email, password } = req.body;
  if (!name || !email || !password) {
    return res.status(400).json({
      error: "Todos os campos são obrigatórios!",
    });
  }

  const user = await User.findOne({ email });
  if (user) {
    return res.status(400).json({
      error: "E-mail já cadastrado!",
    });
  }
  next();
};

exports.validateLogin = async (req, res, next) => {
  const { email, password } = req.body;
  if (!email || !password) {
    return res.status(400).json({
      error: "Todos os campos são obrigatórios!",
    });
  }
  next();
};
