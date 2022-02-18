const router = require("express").Router();
const dateReferenceController = require("../controllers/dateReferenceController");
const isAuth = require("../auth/authorization");

// GET ALL DATE REFERENCES
router.get("/", isAuth, dateReferenceController.getAllDateReferences);

// GET DATE REFERENCES BY MONTH AND YEAR
router.get("/filterdatereferences/:startmonth/:startyear/:endmonth/:endyear", isAuth, dateReferenceController.filterDateReferencesByMonthAndYear);

module.exports = router;
