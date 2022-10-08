import React, {useState, forwardRef} from 'react';
import {TextInput, StyleSheet} from 'react-native';

// Reference forwading for automatic focus of next input onSubmitEditing
// The reference has to be forwarder, because custom components don't use refs on default
const CustomTextInput = forwardRef((props, ref) => {
  const [isFocused, setIsFocused] = useState(false);

  return (
    <TextInput
      {...props}
      style={[
        styles.textInput,
        props.style,
        isFocused && {borderColor: 'rgba(49, 198, 232, 1)', borderRadius: 5},
        props.error && {borderColor: '#F8346C'},
      ]}
      onBlur={() => setIsFocused(false)}
      onFocus={() => setIsFocused(true)}
      ref={ref}
    />
  );
});

const styles = StyleSheet.create({
  textInput: {
    borderColor: 'rgba(49, 198, 232, 0.5)',
    borderWidth: 2,
    borderRadius: 15,
    paddingLeft: 10,
    fontFamily: 'RobotoCondensed-Regular',
    fontSize: 14,
  },
});

export default CustomTextInput;
