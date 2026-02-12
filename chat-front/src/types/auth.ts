export type LoginRequest = {
  email: string;
  password: string;
};

export type LoginResponse = {
  token: string;
  expiration: Date;
};

export type UserRequest = {
  name: string;
  email: string;
  password: string;
};

export type UserResponse = {
  id: string;
  name: string;
  email: string;
};
