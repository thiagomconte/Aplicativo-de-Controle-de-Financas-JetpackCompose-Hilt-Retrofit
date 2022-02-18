const User = require("../models/User");
const jwt = require("jsonwebtoken");
const bcrypt = require("bcryptjs");

// CREATE USER
exports.createUser = async (req, res) => {
  try {
    const { name, email, password } = req.body;
    const user = await User.create({
      name,
      email,
      password: bcrypt.hashSync(password, bcrypt.genSaltSync(10)),
    });
    await user.save();
    return res.status(200).json({
      success: true,
      message: "Sua conta foi criada com sucesso!",
    });
  } catch (error) {
    res.status(500).json({
      error: error.message,
    });
  }
};

// LOGIN USER
exports.loginUser = async (req, res) => {
  try {
    const { email, password } = req.body;
    const user = await User.findOne({ email });
    if (!user) {
      return res.status(400).json({
        error: "Usuário não encontrado!",
      });
    }
    if (!bcrypt.compareSync(password, user.password)) {
      return res.status(400).json({
        error: "Senha incorreta!",
      });
    }
    const token = jwt.sign({ id: user._id, email: user.email }, process.env.SECRET, {
      expiresIn: "7d",
    });
    return res.status(200).json({
      user: {
        name: user.name,
        email: user.email,
      },
      token,
    });
  } catch (error) {
    res.status(500).json({
      error: error.message,
    });
  }
};
