import React, { Component } from 'react';
import Modal from "react-native-modal";
import {NativeModules, NativeEventEmitter, View, Text, StyleSheet, SafeAreaView} from 'react-native';
import PropTypes from 'prop-types';

class PeachMobileModule {

  getCurrentTime = async () => {
    return NativeModules.PeachMobile.getCurrentTime()
  };

  initPaymentProvider(mode: String) {

    return NativeModules.PeachMobile.initPaymentProvider(mode)

  }

  pay(checkoutId: string,
    cardHolder: string,
    cardNumber: string,
    cardExpiryMonth: string,
    cardExpiryYear: string,
    cardCVV: string,
    deviceId: string,
    did: string)  {

      let PaymentResult = NativeModules.PeachMobile.payFull(checkoutId,cardHolder,cardNumber,cardExpiryMonth,cardExpiryYear,cardCVV,deviceId,did);
      console.log('--------------------------------------------PaymentREsult Start -----------------------------------------------------');
      console.log(JSON.stringify(PaymentResult));
      console.log('--------------------------------------------PaymentREsult ENd-----------------------------------------------------');
      return PaymentResult
      
    }

  createPaymentParams(checkoutId: string,
    cardHolder: string,
    cardNumber: string,
    cardExpiryMonth: string,
    cardExpiryYear: string,
    cardCVV: string)  {
      let PaymentParameters = NativeModules.PeachMobile.createPaymentParams(checkoutId,cardHolder,cardNumber,cardExpiryMonth,cardExpiryYear,cardCVV);
      console.log('--------------------------------------------PaymentParameters Start -----------------------------------------------------');
      console.log(JSON.stringify(PaymentParameters));
      console.log('--------------------------------------------PaymentParameters ENd-----------------------------------------------------');
      
      return NativeModules.PeachMobile.createPaymentParams(checkoutId,cardHolder,cardNumber,cardExpiryMonth,cardExpiryYear,cardCVV)
    }

}

const PeachMobile = new PeachMobileModule();
export default PeachMobile;


// export default class PeachMobile extends Component {
  
// render(): React.ReactNode {
//   return (
//         <Text>3D Secure</Text>
// )  
// }
  
//   componentDidMount() {
//     NativeModules.PeachMobile.initPaymentProvider('test');
//     // PeachMobile.setUrlScheme(urlScheme);
// }


// }