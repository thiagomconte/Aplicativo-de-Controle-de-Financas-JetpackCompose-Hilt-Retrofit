const router = require("express").Router();
const UserController = require("../controllers/userController");
const userValidator = require("../validators/userValidator");

// CREATE USER
router.post("/register", userValidator.validateRegister, UserController.createUser);
// LOGIN USER
router.post("/login", userValidator.validateLogin, UserController.loginUser);

module.exports = router;
