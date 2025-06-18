import path, {resolve} from 'path';
import webpack from 'webpack';
import tailwindcss from '@tailwindcss/postcss';
import HtmlWebpackPlugin from 'html-webpack-plugin';

export default {
    entry: [
        ...process.env.NODE_ENV === 'development' ? ['webpack-hot-middleware/client?reload=true'] : [],
        './frontend/index'
    ],
    output: {
        path: resolve(import.meta.dirname, 'dist/public'),
        filename: 'bundle.js'
    },
    devtool: 'source-map',
    mode: process.env.NODE_ENV === 'development' ? 'development' : 'production',
    module: {
        rules: [
            {
                test: /\.(ts|tsx)$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
            {
                test: /\.css$/,
                use: [
                    "style-loader",
                    "css-loader",
                    {
                        loader: "postcss-loader",
                        options: {
                            postcssOptions: {
                                plugins: [
                                    [
                                       tailwindcss,
                                    ],
                                ],
                            },
                        },
                    },
                ],
            },
        ]
    },
    resolve: {
        extensions: ['.js', '.ts', '.tsx'],
    },
    plugins: [
        new webpack.HotModuleReplacementPlugin(),
        new HtmlWebpackPlugin({
            inject: 'body',
            template: path.resolve(import.meta.dirname, 'frontend/index.html'),
        }),
    ]
};
