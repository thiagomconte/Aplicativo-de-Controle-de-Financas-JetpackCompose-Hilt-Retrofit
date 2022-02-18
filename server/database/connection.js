const mongoose = require("mongoose");

var url = process.env.MONGO_URL_DEV;

if (process.env.NODE === "production") {
  url = process.env.MONGO_URL_PROD;
}

mongoose
  .connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
  .then(() => {
    console.log("MongoDB connected");
  })
  .catch((err) => {
    console.log(err);
  });

module.exports = mongoose;
