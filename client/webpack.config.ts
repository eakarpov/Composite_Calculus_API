import * as path from "path";
import * as webpack from "webpack";

const webpackConfig: webpack.Configuration = {
  entry: {
    app: ["./src/app/init.ts"]
  },
  output: {
    path: path.resolve(__dirname, "build"),
    publicPath: "/static/",
    filename: "bundle.js"
  },
  resolve: {
    extensions: ['.ts', '.js']
  },
  devtool: 'source-map',
  module: {
    loaders: [
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {
          loaders: {
            ts: 'ts-loader'
          },
          esModule: true
        }
      },
      {
        test: /\.ts?$/,
        loader: 'ts-loader',
        options: {
          configFile: 'tsconfig.json',
          appendTsSuffixTo: [/\.vue$/]
        },
      },
      {
        test: /\.scss$/,
        loaders: [
          'style-loader',
          'css-loader'
        ]
      }
    ]
  }
};

export default webpackConfig;