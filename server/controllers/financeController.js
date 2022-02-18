const Finance = require("../models/Finance");
const DateReference = require("../models/DateReference");
const financeType = require("../utils/FinanceType");
const moment = require("moment");

// NEW FINANCE
exports.createFinance = async (req, res) => {
  try {
    const { description, value, date, type } = req.body;
    const momentDate = moment(date, "YYYY-MM-DD");
    let dateReference = await DateReference.findOne({ month: momentDate.month(), year: momentDate.year() });
    if (!dateReference) {
      dateReference = await DateReference.create({
        month: momentDate.month(),
        year: momentDate.year(),
        value: getValue(value, type),
        userId: req.user.id,
      });
    } else {
      if (type === financeType.PROFIT) {
        await DateReference.findByIdAndUpdate(dateReference._id, {
          value: dateReference.value + value,
        });
      } else {
        await DateReference.findByIdAndUpdate(dateReference._id, {
          value: dateReference.value - value,
        });
      }
    }
    const finance = await Finance.create({
      userId: req.user.id,
      dateReferenceId: dateReference._id,
      description,
      value: getValue(value, type),
      date: momentDate,
      type,
    });
    await finance.save();
    await dateReference.save();
    return res.status(200).json({
      error: "Resumo criado com sucesso!",
    });
  } catch (error) {
    res.status(500).json({
      error: error.message,
    });
  }
};

// GET FINANCES BY MONTH AND YEAR
exports.getAllFinances = async (req, res) => {
  try {
    const finances = await Finance.find({ userId: req.user.id, dateReferenceId: req.params.dateReferenceId }).sort({ date: 1 });
    return res.status(200).json({
      data: finances,
    });
  } catch (error) {
    res.status(500).json({
      error: error.message,
    });
  }
};

// FILTER FINANCES
exports.getFilteredFinances = async (req, res) => {
  try {
    const { startmonth, startyear, endmonth, endyear } = req.params;
    const finances = await Finance.find({
      date: {
        $gte: new Date(startyear, startmonth, 1),
        $lte: new Date(endyear, endmonth, 31),
      },
    });
    return res.status(200).json({
      data: finances,
    });
  } catch (error) {
    res.status(500).json({
      error: error.message,
    });
  }
};

// FUNCTIONS
function getValue(value, type) {
  if (type === financeType.PROFIT) {
    return value;
  } else {
    return value * -1;
  }
}
