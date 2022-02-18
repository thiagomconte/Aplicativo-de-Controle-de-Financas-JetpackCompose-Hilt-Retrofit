const DateReference = require("../models/DateReference");

exports.getAllDateReferences = async (req, res) => {
  try {
    const dateReferences = await DateReference.find({ userId: req.user.id }).sort({ year: -1, month: -1 });
    return res.status(200).json({
      data: dateReferences,
    });
  } catch (error) {
    res.status(500).json({
      error: error.message,
    });
  }
};

exports.filterDateReferencesByMonthAndYear = async (req, res) => {
  try {
    const { startMonth, startYear, endMonth, endYear } = req.body;
    const dateReferences = await DateReference.find({
      userId: req.user.id,
      month: {
        $gte: startMonth,
        $lte: endMonth,
      },
      year: {
        $gte: startYear,
        $lte: endYear,
      },
    });
    return res.status(200).json({
      data: dateReferences,
    });
  } catch (error) {
    res.status(500).json({
      error: error.message,
    });
  }
};
