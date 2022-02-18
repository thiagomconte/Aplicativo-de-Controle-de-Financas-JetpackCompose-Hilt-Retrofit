const router = require("express").Router();
const financeController = require("../controllers/financeController");
const isAuth = require("../auth/authorization");
const financeValidator = require("../validators/financeValidator");

// NEW FINANCE
router.post("/", isAuth, financeValidator.validateNewFinance, financeController.createFinance);
// GET ALL FINANCES
router.get("/:dateReferenceId", isAuth, financeController.getAllFinances);
// GET FINANCES BY MONTH AND YEAR
router.get("/filterfinances/:startmonth/:startyear/:endmonth/:endyear", isAuth, financeController.getFilteredFinances);

module.exports = router;
