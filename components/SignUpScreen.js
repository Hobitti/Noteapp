import React, {useState, createRef} from 'react';
import {Text, View, StyleSheet, Image} from 'react-native';

import CustomButton from './CustomButton';
import CustomTextInput from './CustomTextInput';
import KeyboardAvoidingInputs from './KeyboardAvoidingInputs';

import Icon from 'react-native-vector-icons/FontAwesome5';
import Toast from 'react-native-toast-message';

const SignInScreen = props => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConf, setPasswordConf] = useState('');
  const [passHidden, setPassHidden] = useState(true);
  const [passConfHidden, setPassConfHidden] = useState(true);
  const [nameError, setNameError] = useState('');
  const [passError, setPassError] = useState('');
  const [passConfError, setPassConfError] = useState('');

  let passwordInputRef = createRef();
  let passwordInputConfRef = createRef();

  const checkRegistration = async () => {
    let hasError = false;

    if (username.trim() === '') {
      setNameError('Username is required');
      hasError = true;
    } else {
      setNameError('');
    }

    if (password.trim() === '') {
      setPassError('Password is required');
      setPassConfError('');
      hasError = true;
    } else if (password !== passwordConf) {
      setPassError("Password's don't match");
      setPassConfError("Password's don't match");
      hasError = true;
    } else {
      setPassError('');
      setPassConfError('');
    }

    if (!hasError) {
      try {
        let response = await fetch(
          'http://10.0.2.2:8080/rest/noteappservice/registeruser',
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
        if (json) {
          Toast.show({
            type: 'success',
            text1: 'Registration succesful',
            text2: 'You will be redirected to login',
          });
          props.navigation.navigate('SignInScreen', {username: username});
        } else {
          Toast.show({
            type: 'error',
            text1: 'Registration failed',
            text2: 'Something went wrong. Try again later',
          });
        }
      } catch (error) {
        console.log(error);
        Toast.show({
          type: 'error',
          text1: 'Registration failed',
          text2: 'Server error. Try again later',
        });
      }
    } else {
      Toast.show({
        type: 'error',
        text1: 'Registration failed',
        text2: 'Please correct errors',
      });
    }
  };

  return (
        <KeyboardAvoidingInputs>
          <View style={styles.componentContainer}>
            <View style={styles.contentContainer}>
              <View style={styles.logo}>
                <Image source={require('../assets/images/logo.png')} />
              </View>
              <View style={styles.form}>
                <View style={styles.formTitle}>
                  <Text style={styles.header}>Sign Up</Text>
                </View>
                <View style={styles.formInput}>
                  <Text style={styles.inputTitle}>Username</Text>
                  <CustomTextInput
                    value={username}
                    placeholder="Enter username"
                    onChangeText={text => {
                      setUsername(text);
                    }}
                    maxLength={30}
                    error={nameError}
                    onSubmitEditing={() => passwordInputRef.current.focus()}
                    blurOnSubmit={false}
                  />
                  {!!nameError && <Text style={styles.error}>{nameError}</Text>}
                </View>
                <View style={styles.formInput}>
                  <Text style={styles.inputTitle}>Password</Text>
                  <View style={styles.passwordInput}>
                    <CustomTextInput
                      value={password}
                      placeholder="Enter password"
                      secureTextEntry={passHidden}
                      onChangeText={text => {
                        setPassword(text);
                      }}
                      maxLength={40}
                      error={passError}
                      ref={passwordInputRef}
                      onSubmitEditing={() => passwordInputConfRef.current.focus()}
                      blurOnSubmit={false}
                    />
                    <View style={styles.iconInInput}>
                      <Icon
                        onPress={() => setPassHidden(!passHidden)}
                        name={passHidden ? 'eye' : 'eye-slash'}
                        style={styles.icon}
                        color={
                          passHidden
                            ? 'rgba(49, 198, 232, 1)'
                            : 'rgba(49, 198, 232, 1)'
                        }
                      />
                    </View>
                  </View>
                  {!!passError && <Text style={styles.error}>{passError}</Text>}
                </View>
                <View style={styles.formInput}>
                  <Text style={styles.inputTitle}>Confirm password</Text>
                  <View style={styles.passwordInput}>
                    <CustomTextInput
                      value={passwordConf}
                      placeholder="Enter password again"
                      secureTextEntry={passConfHidden}
                      onChangeText={text => {
                        setPasswordConf(text);
                      }}
                      maxLength={40}
                      error={passConfError}
                      ref={passwordInputConfRef}
                    />
                    <View style={styles.iconInInput}>
                      <Icon
                        onPress={() => setPassConfHidden(!passConfHidden)}
                        name={passConfHidden ? 'eye' : 'eye-slash'}
                        style={styles.icon}
                        color={
                          passConfHidden
                            ? 'rgba(49, 198, 232, 1)'
                            : 'rgba(49, 198, 232, 1)'
                        }
                      />
                    </View>
                  </View>
                  {!!passConfError && (
                    <Text style={styles.error}>{passConfError}</Text>
                  )}
                </View>
                <View style={styles.formButtons}>
                  <View style={styles.formButton}>
                    <CustomButton
                      title="register"
                      type="main"
                      onPress={checkRegistration}
                    />
                  </View>
                  <View style={styles.formButton}>
                    <CustomButton
                      title="cancel"
                      type="secondary"
                      onPress={() => props.navigation.navigate('SignInScreen')}
                    />
                  </View>
                </View>
              </View>
            </View>
          </View>
        </KeyboardAvoidingInputs>
  );
};

const styles = StyleSheet.create({
  componentContainer: {
    flex: 1,
    backgroundColor: 'rgba(248, 52, 108, 0.7)',
  },
  contentContainer: {
    flex: 1,
    backgroundColor: '#fff',
    top: 80,
    borderTopLeftRadius: 40,
    borderTopRightRadius: 40,
    alignItems: 'center',
  },
  logo: {
    bottom: 66.5,
  },
  form: {
    flex: 1,
    width: 230,
    bottom: 20,
  },
  formTitle: {
    alignItems: 'center',
    bottom: 25,
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
  formButtons: {
    marginTop: 20,
  },
  formButton: {
    marginBottom: 15,
  },
  passwordInput: {
    position: 'relative',
  },
  icon: {
    fontSize: 18,
  },
  iconInInput: {
    position: 'absolute',
    top: 0,
    right: 8,
    bottom: 0,
    justifyContent: 'center',
  },
  error: {
    fontSize: 14,
    color: '#F8346C',
    marginLeft: 10,
  },
});

export default SignInScreen;
