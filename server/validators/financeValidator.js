const moment = require("moment");
exports.validateNewFinance = async (req, res, next) => {
  const { description, value, date, type } = req.body;
  try {
    if (!description || !value || !date || !type) {
      return res.status(400).json({
        error: "Todos os campos são obrigatórios!",
      });
    }
    next();
  } catch (error) {
    res.status(500).json({
      error: error.message,
    });
  }
};
