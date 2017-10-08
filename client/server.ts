import * as express from "express";
import * as webpack from "webpack";
import * as webpackMiddleware from "webpack-dev-middleware";
import * as webpackHotMiddleware from "webpack-hot-middleware";

import config from "./webpack.config";
import * as path from "path";

const app: express.Application  = express();
const compiler = webpack(config);

app.use(webpackMiddleware(compiler, {
  publicPath: config.output.publicPath,
}));

app.use(webpackHotMiddleware(compiler));

// ../public path for /build package after compiling
app.use(express.static(path.join(__dirname, "../public")));

app.listen(3000, () => {
  console.log("Server is listening on port 3000!");
});