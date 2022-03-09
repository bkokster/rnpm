// import React, { useEffect, useState } from 'react'
// import { SafeAreaView, ScrollView, View, Text, Button } from 'react-native'
// import { StyleSheet, TextInput } from "react-native";
// import PeachMobileSimple from './PeachMobileSimple'
// import PeachMobile from './PeachMobile'
// import PeachMobileComponent from './CodehesionPeachMobile'

//  const App = () => {
  
//   const [date, setDate] = useState<string>('');
//   const [teststring, setString] = useState<string>('');

//   // useEffect(() => {
//   //   PeachMobile.getTestString().then((testString: string) => {
//   //     setDate(testString);
//   //   });
//   // }, []);

// // PeachMobileSimple.createPaymentParams('9553627D8171C5990C31846811315F2D.uat01-vm-tx04','John Doe','4012888888881881','12','23','123')

// PeachMobile.initPaymentProvider('test')
// PeachMobile.pay('FB94AFCF075ACA6B383B33C27A8A0E3F.uat01-vm-tx02','benji','4012888888881881','12','23','123','123456','did:ethr:0xf3beac30c498d9e26865f34fcaa57dbb935b0d74')

    
//   return (
//     // <PeachMobile>sgdsg</PeachMobile>
//     <View>
//       <Text>Initialisation Check</Text>
//     </View>
//   );

// }

// const styles = StyleSheet.create({
//   input: {
//     height: 40,
//     margin: 12,
//     borderWidth: 1,
//     padding: 10,
//   },
// });

// export default App

import PeachMobile from './PeachMobile'
export default PeachMobile