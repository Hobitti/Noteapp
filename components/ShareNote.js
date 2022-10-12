import React, {useState} from 'react';
import {StyleSheet, Text, View, TextInput, Button, TouchableOpacity } from 'react-native';

const App = (userID) => {
  const [inputID, setIdNote,]=useState();
  const [inputUserID, setUserNote,]=useState();
  const [inputReceiverID, setReceiverNote,]=useState();
  const [list, addShareID]=useState();
  const [shareData, setShareData]=useState();
  class sharingNote {
    constructor(noteID, distributorID) {
      this.noteID = noteID;
      this.distributorID = distributorID
    }
  }
  const sharenote=new sharingNote("","")

  function setShareID(list) {
    addShareID(list.shareID);
  }


  const noteNameInputHandler=(input)=>{
    setIdNote(input)
  }

  const noteTextInputHandler=(input)=>{
    setUserNote(input)
  }
  const noteReceiverInputHandler=(input)=>{
    setReceiverNote(input)
  }

  const addDataToList=()=>{
    sharenote.noteID=inputID;
    sharenote.distributorID=inputUserID;
    console.log(sharenote)
  }
  const AppButton = ({onPress, title, type}) => (
    <TouchableOpacity
      onPress={onPress}
      style={
        type == 'main'
          ? styles.appButtonContainerMain
          : styles.appButtonContainerSecondary
      }>
      <Text
        style={
          type == 'main'
            ? styles.appButtonTextMain
            : styles.appButtonTextSecondary
        }>
        {title}
      </Text>
    </TouchableOpacity>
  );



  const getShare = async () => {
    try {
      addDataToList();
      let response = await fetch(
        'http://10.0.2.2:8080/rest/noteappservice/getshare',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({distributorID: sharenote.distributorID, noteID: sharenote.noteID/*distributorID: '2', noteID: '2'*/}),
        },
      );

      let json = await response.json();
      console.log("getShare: "+json);
      setShareID(json);
    } catch (error) {
      console.log(error);
    }
  };
  //         Share note to user
  const shareNoteToUser = async () => {
    try {
      addDataToList();
      let response = await fetch(
        'http://10.0.2.2:8080/rest/noteappservice/sharetouser',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({shareID: list, userID: inputReceiverID}),
        },
      );

      console.log("list.shareid: "+list)
      console.log("list.inputreceiverid: "+inputReceiverID)
      let json = await response.text();
      //setShare(json);
    } catch (error) {
      console.log(error);
    }
  };

  //      Share note
  const shareNote = async () => {
    try {
      addDataToList();
      let response = await fetch(
        'http://10.0.2.2:8080/rest/noteappservice/sharenote',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({distributorID: sharenote.distributorID, noteID: sharenote.noteID}),
        },
      );

      let json = await response.text();
      console.log("sharenote receiverid: "+inputReceiverID);
      getShare();
      shareNoteToUser(); //Liikuta tää muualle
    } catch (error) {
      console.log(error);
    }
  };




  return (
        <View style={styles.mainContainer}>
          <View style={styles.contentContainer}>
            <View style={styles.form}>
              <View style={styles.formTitle}>
                <Text style={styles.header}>Share note </Text>
               </View>
              <TextInput style={styles.textInput} placeholder="Note ID" onChangeText={(text)=>(noteNameInputHandler(text))} />
              <TextInput style={styles.textInput} placeholder="User ID" onChangeText={(text)=>(noteTextInputHandler(text))} />
              <TextInput style={styles.textInput} placeholder="Receiver ID" onChangeText={(text)=>(noteReceiverInputHandler(text))} />
              <View style={styles.checkboxContainer}>
              </View>
              <View style={styles.formButtons}>
                <View style={styles.formButton}> 
                  <AppButton   title='Share Note'  type="main" onPress={shareNote} />
                </View>
              </View>
            </View>
          </View>
        </View>
        

  );
}


const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'rgba(248, 52, 108, 0.7)',
  },
  contentContainer: {
    flex: 1,
    backgroundColor: '#fff',
    top: 110,
    borderTopLeftRadius: 40,
    borderTopRightRadius: 40,
    alignItems: 'center',
  },
  logo: {
    bottom: 66.5,
  },
  form: {
    flex: 1,
    width: 220,
  },
  formTitle: {
    alignItems: 'center',
    bottom: 30,
  },
  header: {
    fontFamily: 'RobotoCondensed-Bold',
    fontSize: 30,
    color: '#000',
  },
  formInput: {
    marginBottom: 15,
  },
  inputTitle: {
    fontFamily: 'RobotoCondensed-Regular',
    color: '#000',
    marginLeft: 10,
    fontSize: 16,
  },
  textInput: {
    borderColor: 'rgba(49, 198, 232, 0.5)',
    borderWidth: 2,
    borderRadius: 15,
    paddingLeft: 10,
    fontFamily: 'RobotoCondensed-Regular',
    fontSize: 14,
    marginBottom: 15,
  },
  textInputLong: {
    borderColor: 'rgba(49, 198, 232, 0.5)',
    borderWidth: 2,
    borderRadius: 15,
    paddingLeft: 10,
    fontFamily: 'RobotoCondensed-Regular',
    fontSize: 14,
    height: 150,
    marginBottom: 15,
  },
  formButtons: {
    marginTop: 20,
  },
  formButton: {
    marginBottom: 15,
  },
  appButtonContainerMain: {
    elevation: 6,
    backgroundColor: '#31E89F',
    borderRadius: 15,
    paddingVertical: 12,
    paddingHorizontal: 12,
  },
  appButtonTextMain: {
    fontSize: 16,
    color: '#fff',
    fontFamily: 'RobotoCondensed-Bold',
    alignSelf: 'center',
    textTransform: 'uppercase',
  },
  appButtonContainerSecondary: {
    elevation: 6,
    backgroundColor: '#fff',
    borderColor: '#31E89F',
    borderWidth: 2,
    borderRadius: 15,
    paddingVertical: 12,
    paddingHorizontal: 12,
  },
  appButtonTextSecondary: {
    fontSize: 16,
    color: '#000',
    fontFamily: 'RobotoCondensed-Bold',
    alignSelf: 'center',
    textTransform: 'uppercase'
  },
  passwordInput: {
    position: 'relative'
  },
  icon: {
    fontSize: 18
  },
  iconInInput: {
    position: 'absolute',
    top: 0,
    right: 8,
    bottom: 0,
    justifyContent: 'center',
  },
});

export default App;