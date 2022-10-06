import React, {useState} from 'react';
import {
  StyleSheet,
  Text,
  View,
  TextInput,
  Image,
  TouchableOpacity,
} from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome5';

const App = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [passHidden, setPassHidden] = useState(true);

  const usernameHandler = text => {
    setUsername(text);
  };

  const passwordHandler = text => {
    setPassword(text);
  };

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

  const checkLogin = async () => {
    try {
      let response = await fetch(
        'http://10.0.2.2:8080/rest/noteappservice/checkuserlogin',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({username: username, password: password}),
        },
      );

      let json = await response.json();
      console.log(json);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <View style={styles.mainContainer}>
      <View style={styles.contentContainer}>
        <View style={styles.logo}>
          <Image source={require('./assets/images/logo.png')} />
        </View>

        <View style={styles.form}>
          <View style={styles.formTitle}>
            <Text style={styles.header}>Sign In</Text>
          </View>

          <View style={styles.formInput}>
            <Text style={styles.inputTitle}>Username</Text>
            <TextInput
              style={styles.textInput}
              placeholder="Enter your username"
              onChangeText={usernameHandler}
            />
          </View>

          <View style={styles.formInput}>
            <Text style={styles.inputTitle}>Password</Text>
            <View style={styles.passwordInput}>
              <TextInput
                style={styles.textInput}
                placeholder="Enter your password"
                onChangeText={passwordHandler}
                secureTextEntry={passHidden}
              />
              <View style={styles.iconInInput}>
                <Icon onPress={() => setPassHidden(!passHidden)} name={passHidden ? "eye" : "eye-slash"} style={styles.icon} color={passHidden ? "rgba(49, 198, 232, 1)" : "rgba(49, 198, 232, 1)"} />
              </View>
            </View>
          </View>

          <View style={styles.formButtons}>
            <View style={styles.formButton}>
              <AppButton title="login" type="main" onPress={checkLogin} />
            </View>
            <View style={styles.formButton}>
              <AppButton title="sign up" type="secondary" />
            </View>
          </View>
        </View>
      </View>
    </View>
  );
};

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
