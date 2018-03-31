import React, { Component } from 'react';

import { Provider as RebassProvider } from 'rebass';

import { Provider as ReduxProvider } from 'react-redux';
import { createStore, applyMiddleware, combineReducers } from 'redux';
import thunk from 'redux-thunk';
import * as reducers from './store/reducers';

import AuthContainer from './containers/AuthContainer';
import Framework from './Framework';

const store = createStore(combineReducers(reducers), applyMiddleware(thunk));

export default class App extends Component {
  render() {
    return (
      <ReduxProvider store={store}>
        <RebassProvider>
          <AuthContainer/>
          <Framework/>
        </RebassProvider>
      </ReduxProvider>
    );
  }
}