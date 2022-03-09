import {NativeModules} from 'react-native';
class PeachMobileSimpleModule {

  getCurrentTime = async () => {
    return NativeModules.PeachMobile.getCurrentTime()
  };

  createPaymentParams(checkoutId: string,
    cardHolder: string,
    cardNumber: string,
    cardExpiryMonth: string,
    cardExpiryYear: string,
    cardCVV: string)  {
      
        NativeModules.PeachMobileSimple.createPaymentParams(checkoutId,cardHolder,cardNumber,cardExpiryMonth,cardExpiryYear,cardCVV)
        // .then((response: { json: () => any; }) => response.json())
        .then((data: any) => {

                console.log('--------------------------------------------PaymentParameters Start -----------------------------------------------------');
                console.log(data);
                console.log('--------------------------------------------PaymentParameters Start -----------------------------------------------------');


        })
        .catch((err: any) => {
         
            console.log('--------------------------------------------Error -----------------------------------------------------');
            console.error(err)
            console.log('--------------------------------------------Error -----------------------------------------------------');
                        
        });
        
    //   console.log('--------------------------------------------PaymentParameters Start -----------------------------------------------------');
    //   console.log(PaymentParameters);
    //   console.log('--------------------------------------------PaymentParameters ENd-----------------------------------------------------');
      
    //   return PaymentParameters;
    }

}

const PeachMobileSimple = new PeachMobileSimpleModule();
export default PeachMobileSimple;