import path from 'path';
import express from 'express';
import webpackDevMiddleware from 'webpack-dev-middleware';
// @ts-ignore
import webpackHotMiddleware from 'webpack-hot-middleware';
import webpack from 'webpack';
import logger from './logger';
// @ts-ignore
import webpackConfig from '../../webpack.config.mjs';
import api from './api';

const PORT = process.env.PORT || 5559;

const app = express();

if (process.env.NODE_ENV === 'development') {
    const bundler = webpack(webpackConfig);
    app.use(webpackDevMiddleware(bundler, {
        publicPath: webpackConfig.output.publicPath,
    }));
    app.use(webpackHotMiddleware(bundler));
}

// BFF API
app.use('/api', api);

// Client static resources
app.use(express.static(path.resolve(__dirname, '../../dist/public')));

app.listen(PORT, () => {
    logger.info('Server available at http://localhost:%s', PORT);
});
