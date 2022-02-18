const { model, Schema } = require("mongoose");

const DateReferenceSchema = new Schema({
  userId: {
    type: Schema.Types.ObjectId,
    ref: "User",
  },
  month: {
    type: Number,
    required: true,
  },
  year: {
    type: Number,
    required: true,
  },
  value: {
    type: Number,
    required: true,
  },
});

module.exports = model("DateReference", DateReferenceSchema);
