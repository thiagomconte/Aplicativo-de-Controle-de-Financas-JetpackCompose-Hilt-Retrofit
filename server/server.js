require("dotenv").config();
const express = require("express");
const app = express();

// DATABASE
require("./database/connection");

// MIDDLEWARES
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// ROUTES 
app.use("/api/v1/users", require("./routes/userRoute"));
app.use("/api/v1/finances", require("./routes/financeRoute"));
app.use("/api/v1/datereferences", require("./routes/dateReferenceRoute"));

// START PORT AND SERVER
const PORT = process.env.PORT || 3000;
app.listen(PORT, (err) => {
  if (err) {
    console.log(err);
  } else {
    console.log(`Server is listening on port ${PORT}`);
  }
});
