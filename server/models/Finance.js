const { model, Schema } = require("mongoose");

const FinanceSchema = new Schema({
  userId: {
    type: Schema.Types.ObjectId,
    ref: "User",
  },
  dateReferenceId: {
    type: Schema.Types.ObjectId,
    ref: "DateReference",
  },
  description: {
    type: String,
    required: true,
  },
  value: {
    type: Number,
    required: true,
  },
  date: {
    type: Date,
    required: true,
  },
  type: {
    type: String,
    required: true,
  },
});

module.exports = model("Finance", FinanceSchema);
