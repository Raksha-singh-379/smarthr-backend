import dayjs from 'dayjs/esm';
import { ILeavePolicy } from 'app/entities/leave-policy/leave-policy.model';
import { IEmployee } from 'app/entities/employee/employee.model';

export interface ICustomLeavePolicy {
  id: number;
  customPolicyName?: string | null;
  leavePolicyId?: number | null;
  days?: number | null;
  companyId?: number | null;
  status?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  leavePolicy?: Pick<ILeavePolicy, 'id'> | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewCustomLeavePolicy = Omit<ICustomLeavePolicy, 'id'> & { id: null };
